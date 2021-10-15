Настройка Keycloak

Пробрасываем порт наружу
```shell
kubectl -n keycloak port-forward service/keycloak-http 8080:80
```

Создаём администратора
```
http://localhost:8080/
admin/ne0f1ex
```

Создаём realm (Add realm)
```
qfactor-realm
```

Создаём роли (Roles->Add Role)
```
user
admin
```

Создаём пользователей (Users->Add User)
Указываем пароли (Users->qadmin->Credentials)
Задаём роли (Users->qadmin->RoleMappings)
```
qadmin/ne0f1ex/admin
quser/ne0f1ex/user
```

Создаём клиента (Clients->Add Client)
```
Client ID: qfactor
Client Protocol: openid-connect
Root URL: http://localhost:8080 
```

Создаём Protocol Mapper (Clients->qfactor->Mappers)
```
Name: realm-roles-mapper
Mapper Type: User Realm Role
Token Claim Name: groups
Claim JSON Type: String
```

Проверяем
```shell
>curl -X POST http://localhost:8080/auth/realms/qfactor-realm/protocol/openid-connect/token -H "content-type: application/x-www-form-urlencoded" -d "client_id=qfactor" -d "username=qadmin" -d "password=ne0f1ex" -d "grant_type=password"
{"access_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzS1dIM3pUQ3RvWUxFRDl1SWJyUFkzNU44UGxQNE5feW5ONzM4NUhuLUhnIn0.eyJleHAiOjE2MzQwMjM2ODMsImlhdCI6MTYzNDAyMzM4MywianRpIjoiMjBjMTY5NTQtNzQ3My00YzZiLWEwMWQtZThmYWY4MWEyNjM4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3FmYWN0b3ItcmVhbG0iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMjU2MDlmZDItMzEwNS00NGEwLTkzYTgtNmM3ZDdkNDUwYWM4IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicWZhY3RvciIsInNlc3Npb25fc3RhdGUiOiI4MTQzMDQ4Mi1jNDYwLTQzMzQtYmE2Yy04MDdhYjAwNTE1ZmQiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1xZmFjdG9yLXJlYWxtIiwib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlciJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6IjgxNDMwNDgyLWM0NjAtNDMzNC1iYTZjLTgwN2FiMDA1MTVmZCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZ3JvdXBzIjpbImRlZmF1bHQtcm9sZXMtcWZhY3Rvci1yZWFsbSIsIm9mZmxpbmVfYWNjZXNzIiwiYWRtaW4iLCJ1bWFfYXV0aG9yaXphdGlvbiIsInVzZXIiXSwicHJlZmVycmVkX3VzZXJuYW1lIjoib3Jsb3YiLCJlbWFpbCI6Im9ybG92QG5lb2ZsZXgucnUifQ.sSSSroHaUIm_6e47LBXtsfOIHVpAq4nVLzux3kcO27PVsb_Xcdcw6BtAyh07Co6WR93fSyBkrZ9Y_o65tC_67ApX3VuYphxcW7YVdfUxUZMGHAh9kV4yFmdUZG2Sh5k4XXKDk2T7dab1uICQBT7zRJeIcjNxDz6pu7VI-9wrEchfOM4C1sddyAiYxxm-JyMgxU1OMWiu6AH-m8ReWiUxZzYOXLWP5BWGn-VarjUTyvCRcetK-ERQbWCENtk2R5NXKWWaBh6Dq0p_tN_G5IXjvmwbI4yny3W8dn3qrirhevV-nOuMB2cLixY1hCalvrTPC34wL0LwVYxgo-7u3-p1dQ","expires_in":300,"refresh_expires_in":1800,"refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzNTYxZWU4Yi1mNTJhLTRkM2YtOTA3NC04ZDRhMjU4MjA5MWQifQ.eyJleHAiOjE2MzQwMjUxODMsImlhdCI6MTYzNDAyMzM4MywianRpIjoiZDc3YzdkMDYtYzJhNC00NzMyLTkwZmItMzE1OGJmMTEzNTU5IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3FmYWN0b3ItcmVhbG0iLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvcWZhY3Rvci1yZWFsbSIsInN1YiI6IjI1NjA5ZmQyLTMxMDUtNDRhMC05M2E4LTZjN2Q3ZDQ1MGFjOCIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJxZmFjdG9yIiwic2Vzc2lvbl9zdGF0ZSI6IjgxNDMwNDgyLWM0NjAtNDMzNC1iYTZjLTgwN2FiMDA1MTVmZCIsInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6IjgxNDMwNDgyLWM0NjAtNDMzNC1iYTZjLTgwN2FiMDA1MTVmZCJ9.UYrUPV8q6AaIlFecZ7jvgl8ea5m_vhgTQ3pk3ud5AOs","token_type":"Bearer","not-before-policy":0,"session_state":"81430482-c460-4334-ba6c-807ab00515fd","scope":"profile email"}
```

Декодируем access_token на https://jwt.io/
