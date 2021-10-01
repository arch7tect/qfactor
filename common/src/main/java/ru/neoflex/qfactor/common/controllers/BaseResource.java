package ru.neoflex.qfactor.common.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class BaseResource {
    private static final Logger LOGGER = Logger.getLogger(BaseResource.class.getName());

    @Inject
    public EntityManager entityManager;

    protected static Sort getSortFromQuery(List<String> sortQuery) {
        Sort result = null;
        for (var q : sortQuery) {
            result = getSortFromQuery(result, q);
        }
        return result;
    }

    protected static Sort getSortFromQuery(Sort result, String sortQuery) {
        if (sortQuery == null) {
            return null;
        }
        var columns = Stream.of(sortQuery.split(",")).map(String::trim).filter(s -> !s.isBlank()).map(s ->
                new Sort.Column(
                        s.charAt(0) == '-' ? s.substring(1) : s,
                        s.charAt(0) == '-' ? Sort.Direction.Descending : Sort.Direction.Ascending
                )
        ).toArray(Sort.Column[]::new);
        for (var column : columns) {
            if (result == null) {
                result = Sort.by(column.getName(), column.getDirection());
            } else {
                result.and(column.getName(), column.getDirection());
            }
        }
        return result;
    }

    protected <T extends PanacheEntity> List<T> getTList(
            String filter,
            List<String> sortQuery,
            int pageIndex,
            int pageSize
    ) {
        Sort sort = getSortFromQuery(sortQuery);
        var query = Objects.nonNull(filter) && !filter.isBlank() ?
                (sort != null ? T.find(filter, sort): T.find(filter)) :
                (sort != null ? T.findAll(sort) : T.findAll());
        if (pageIndex > 0 && pageSize > 0) {
            query = query.page(pageIndex, pageSize);
        }
        return query.list();
    }

    protected <T extends PanacheEntity> T getT(
            Long id
    ) {
        T result = T.findById(id);
        if (result == null) {
            throw new WebApplicationException(404);
        }
        return result;
    }

    protected <T extends PanacheEntity> T insertT(
            T entity
    ) {
        entityManager.persist(entity);
        return entity;
    }

    protected <T extends PanacheEntity> T updateT(
            Long id,
            T entity
    ) {
        entity.id = id;
        return entityManager.merge(entity);
    }

    protected <T extends PanacheEntity> void deleteT(
            Long id
    ) {
        entityManager.remove(getT(id));
    }

    protected List<Object> getQueryResults(String query, List<Object> params) throws JsonProcessingException {
        var q = entityManager.createQuery(query);
        var mapper = new ObjectMapper();
        for (var p: q.getParameters()) {
            var value = params.get(p.getPosition() - 1);
            var tree = mapper.valueToTree(value);
            var pt = p.getParameterType();
            var pv = mapper.treeToValue(tree, pt);
            q.setParameter(p.getPosition(), pv);
        }
        return q.getResultList();
    }
}
