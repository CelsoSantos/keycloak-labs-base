# Log

> NOTE:
> Open HAR files with <https://toolbox.googleapps.com/apps/har_analyzer/>

## First login (no user exists before this)

### Keycloak Logs

```log
19:02:22,050 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProviderFactory] (default task-2) [I00] creating new CustomUserStorageProvider
19:02:22,061 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I03] getUserByEmail(some.user@thirdparty.com)
19:02:22,061 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I04] findUser(identifier=some.user@thirdparty.com)
19:02:22,062 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] getFromStorage(identifier=some.user@thirdparty.com)
19:02:22,062 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] Trying to find user some.user@thirdparty.com in userLocalStorage...
19:02:22,063 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I04] no user in userLocalStorage...
19:02:22,063 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I04] creating temporary FederatedAdapter...
19:02:22,066 INFO  [com.mydomain.auth.provider.user.FederatedUserAdapter] (default task-2) [I101] create new FederatedUserAdapter with ID f:254c26a6-d976-4a5d-9ba7-fca8d1da38d2:some.user@thirdparty.com
19:02:22,103 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I06] supportsCredentialType(password)
19:02:22,103 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I01] getUserById(f:254c26a6-d976-4a5d-9ba7-fca8d1da38d2:some.user@thirdparty.com)
19:02:22,104 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I04] findUser(identifier=some.user@thirdparty.com)
19:02:22,104 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] getFromStorage(identifier=some.user@thirdparty.com)
19:02:22,104 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] Trying to find user some.user@thirdparty.com in userLocalStorage...
19:02:22,105 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I04] no user in userLocalStorage...
19:02:22,105 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I04] creating temporary FederatedAdapter...
19:02:22,106 INFO  [com.mydomain.auth.provider.user.FederatedUserAdapter] (default task-2) [I101] create new FederatedUserAdapter with ID f:254c26a6-d976-4a5d-9ba7-fca8d1da38d2:some.user@thirdparty.com
19:02:22,111 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I08] isValid(realm=mydomain,user=some.user@thirdparty.com,credentialInput.type=password)
19:02:22,112 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I06] supportsCredentialType(password)
19:02:22,930 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I09] addToStorage(identifier=some.user@thirdparty.com)
19:02:22,930 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] getFromStorage(identifier=some.user@thirdparty.com)
19:02:22,930 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] Trying to find user some.user@thirdparty.com in userLocalStorage...
19:02:22,931 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I09] User not found in userLocalStorage...
19:02:22,931 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I09] Adding to userLocalStorage...
19:02:22,944 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I09] Setting attributes...
19:02:22,945 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I10] setUserAttributes()
19:02:22,946 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I09] Setting roles...
19:02:22,946 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I11] getRoleModels()
19:02:22,949 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I12] getRoleModel()
19:02:22,965 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I12] getRoleModel()
19:02:22,971 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I09] Setting Federation link and email status...
19:02:22,971 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I09] Model ID: 254c26a6-d976-4a5d-9ba7-fca8d1da38d2
19:02:22,972 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I09] Cleaning session userCache...
19:02:22,983 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I06] supportsCredentialType(otp)
19:02:22,991 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I01] getUserById(f:254c26a6-d976-4a5d-9ba7-fca8d1da38d2:some.user@thirdparty.com)
19:02:22,991 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I04] findUser(identifier=some.user@thirdparty.com)
19:02:22,991 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] getFromStorage(identifier=some.user@thirdparty.com)
19:02:22,992 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] Trying to find user some.user@thirdparty.com in userLocalStorage...
19:02:23,263 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I01] getUserById(f:254c26a6-d976-4a5d-9ba7-fca8d1da38d2:some.user@thirdparty.com)
19:02:23,263 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I04] findUser(identifier=some.user@thirdparty.com)
19:02:23,263 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] getFromStorage(identifier=some.user@thirdparty.com)
19:02:23,263 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] Trying to find user some.user@thirdparty.com in userLocalStorage...
19:02:23,270 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I01] getUserById(f:254c26a6-d976-4a5d-9ba7-fca8d1da38d2:some.user@thirdparty.com)
19:02:23,271 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I04] findUser(identifier=some.user@thirdparty.com)
19:02:23,271 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] getFromStorage(identifier=some.user@thirdparty.com)
19:02:23,271 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] Trying to find user some.user@thirdparty.com in userLocalStorage...
19:02:23,308 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I99] close()
```

### Resulting Token

```
eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwWDFnb1ctaU9HS3VTUzhVVU5SbnBKMUItWFVzcm44WmU0M1ZndTdmUlVjIn0.eyJleHAiOjE2NDU0NzEwNDMsImlhdCI6MTY0NTQ3MDE0MywiYXV0aF90aW1lIjoxNjQ1NDcwMTQzLCJqdGkiOiJhNmM4OWI1YS04MWEzLTQzMzktYjg3My04MTc5MWEwNGE1MWUiLCJpc3MiOiJodHRwOi8va2V5Y2xvYWs6OTkwMC9hdXRoL3JlYWxtcy9teWRvbWFpbiIsImF1ZCI6WyJodHRwczovL2Rldi5teWRvbWFpbi5jb20iLCJodHRwOi8vZGV2LnRoaXJkLXBhcnR5LmNvbS9hcGkvdjIvIiwiYWNjb3VudCJdLCJzdWIiOiJmOjI1NGMyNmE2LWQ5NzYtNGE1ZC05YmE3LWZjYThkMWRhMzhkMjpzb21lLnVzZXJAdGhpcmRwYXJ0eS5jb20iLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJpYW0iLCJub25jZSI6ImU4YmMwN2FkLTgzZTctNDEwYy04MDgzLTRlMDdiZGU3NzZjNCIsInNlc3Npb25fc3RhdGUiOiJkMGUwODdhNy1lZTAzLTQ3YjQtYWNhYy0wMDY1NDRmYzRhN2UiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImxjbS1iYXNpYyIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1teWRvbWFpbiIsInVtYV9hdXRob3JpemF0aW9uIiwiYWMtY29uc3VtZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBtb250YmxhbmMgcHJvZmlsZSIsInNpZCI6ImQwZTA4N2E3LWVlMDMtNDdiNC1hY2FjLTAwNjU0NGZjNGE3ZSIsImh0dHA6Ly9teWRvbWFpbi5jb20vb3JnYW5pemF0aW9uX2lkIjoiNDkxYTZlYjctOWVhMS00NjEyLWE5YjMtYmZhMjU5MzViYmIyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImh0dHA6Ly9teWRvbWFpbi5jb20vdXNlcl9yb2xlcyI6WyJsY20tYmFzaWMiLCJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtbXlkb21haW4iLCJ1bWFfYXV0aG9yaXphdGlvbiIsImFjLWNvbnN1bWVyIl0sImh0dHA6Ly9teWRvbWFpbi5jb20vZW1haWwiOiJzb21lLnVzZXJAdGhpcmRwYXJ0eS5jb20iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzb21lLnVzZXJAdGhpcmRwYXJ0eS5jb20iLCJlbWFpbCI6InNvbWUudXNlckB0aGlyZHBhcnR5LmNvbSJ9.d4dHeXChVNfPnw_OAqEUInc6raKVSKS7zqYWNRbCci--mwtWAHVRtu5OS8euGXjT7xOKQuCx0lcXnn_sSd6E1K16tO1RjulyUfhrPQTBMB_XkLDpynONED9ZiAUGBlNeL4NSPAIFzLZcjaDFjEddYKTb_50D-YD8zikan2wjZjGf2OjoXzIfngsOiyI_H18sagTQTG1LcrqVEmivWInLsxVPh59yLo1GDahOD-EQPZsAc1PQiOElJG96QNEb73l1iu4UHLf8wOeMV5kAY3BdnQNuV1hpp2AQ0ofOikLxnacxqgQswEe2dKKXt3anD3yzAyTgbgeWq2p2nbArLF449Q
```

To see on [jwt.io](https://jwt.io/), click [here](https://jwt.io/#debugger-io?token=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwWDFnb1ctaU9HS3VTUzhVVU5SbnBKMUItWFVzcm44WmU0M1ZndTdmUlVjIn0.eyJleHAiOjE2NDU0NzEwNDMsImlhdCI6MTY0NTQ3MDE0MywiYXV0aF90aW1lIjoxNjQ1NDcwMTQzLCJqdGkiOiJhNmM4OWI1YS04MWEzLTQzMzktYjg3My04MTc5MWEwNGE1MWUiLCJpc3MiOiJodHRwOi8va2V5Y2xvYWs6OTkwMC9hdXRoL3JlYWxtcy9teWRvbWFpbiIsImF1ZCI6WyJodHRwczovL2Rldi5teWRvbWFpbi5jb20iLCJodHRwOi8vZGV2LnRoaXJkLXBhcnR5LmNvbS9hcGkvdjIvIiwiYWNjb3VudCJdLCJzdWIiOiJmOjI1NGMyNmE2LWQ5NzYtNGE1ZC05YmE3LWZjYThkMWRhMzhkMjpzb21lLnVzZXJAdGhpcmRwYXJ0eS5jb20iLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJpYW0iLCJub25jZSI6ImU4YmMwN2FkLTgzZTctNDEwYy04MDgzLTRlMDdiZGU3NzZjNCIsInNlc3Npb25fc3RhdGUiOiJkMGUwODdhNy1lZTAzLTQ3YjQtYWNhYy0wMDY1NDRmYzRhN2UiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImxjbS1iYXNpYyIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1teWRvbWFpbiIsInVtYV9hdXRob3JpemF0aW9uIiwiYWMtY29uc3VtZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBtb250YmxhbmMgcHJvZmlsZSIsInNpZCI6ImQwZTA4N2E3LWVlMDMtNDdiNC1hY2FjLTAwNjU0NGZjNGE3ZSIsImh0dHA6Ly9teWRvbWFpbi5jb20vb3JnYW5pemF0aW9uX2lkIjoiNDkxYTZlYjctOWVhMS00NjEyLWE5YjMtYmZhMjU5MzViYmIyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImh0dHA6Ly9teWRvbWFpbi5jb20vdXNlcl9yb2xlcyI6WyJsY20tYmFzaWMiLCJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtbXlkb21haW4iLCJ1bWFfYXV0aG9yaXphdGlvbiIsImFjLWNvbnN1bWVyIl0sImh0dHA6Ly9teWRvbWFpbi5jb20vZW1haWwiOiJzb21lLnVzZXJAdGhpcmRwYXJ0eS5jb20iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzb21lLnVzZXJAdGhpcmRwYXJ0eS5jb20iLCJlbWFpbCI6InNvbWUudXNlckB0aGlyZHBhcnR5LmNvbSJ9.d4dHeXChVNfPnw_OAqEUInc6raKVSKS7zqYWNRbCci--mwtWAHVRtu5OS8euGXjT7xOKQuCx0lcXnn_sSd6E1K16tO1RjulyUfhrPQTBMB_XkLDpynONED9ZiAUGBlNeL4NSPAIFzLZcjaDFjEddYKTb_50D-YD8zikan2wjZjGf2OjoXzIfngsOiyI_H18sagTQTG1LcrqVEmivWInLsxVPh59yLo1GDahOD-EQPZsAc1PQiOElJG96QNEb73l1iu4UHLf8wOeMV5kAY3BdnQNuV1hpp2AQ0ofOikLxnacxqgQswEe2dKKXt3anD3yzAyTgbgeWq2p2nbArLF449Q)

---

## Refresh page

### Keycloak Logs

```log
19:03:12,358 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProviderFactory] (default task-2) [I00] creating new CustomUserStorageProvider
19:03:12,358 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I01] getUserById(f:254c26a6-d976-4a5d-9ba7-fca8d1da38d2:some.user@thirdparty.com)
19:03:12,359 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I04] findUser(identifier=some.user@thirdparty.com)
19:03:12,359 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] getFromStorage(identifier=some.user@thirdparty.com)
19:03:12,359 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I05] Trying to find user some.user@thirdparty.com in userLocalStorage...
19:03:12,411 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-2) [I99] close()
```

### Resulting Token

```
eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwWDFnb1ctaU9HS3VTUzhVVU5SbnBKMUItWFVzcm44WmU0M1ZndTdmUlVjIn0.eyJleHAiOjE2NDU0NzEwOTIsImlhdCI6MTY0NTQ3MDE5MiwiYXV0aF90aW1lIjoxNjQ1NDcwMTQzLCJqdGkiOiJmMzI1MzYzNy00MmRmLTQ1MmYtYmYzNy0yMDBmYTVmOTlkMzkiLCJpc3MiOiJodHRwOi8va2V5Y2xvYWs6OTkwMC9hdXRoL3JlYWxtcy9teWRvbWFpbiIsImF1ZCI6WyJodHRwczovL2Rldi5teWRvbWFpbi5jb20iLCJodHRwOi8vZGV2LnRoaXJkLXBhcnR5LmNvbS9hcGkvdjIvIiwiYWNjb3VudCJdLCJzdWIiOiJzb21lLnVzZXJAdGhpcmRwYXJ0eS5jb20iLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJpYW0iLCJub25jZSI6ImEwZjc1Y2I3LTZmNzQtNGQ1NC1iMjk1LWMyNDhhYjNhZjZiYyIsInNlc3Npb25fc3RhdGUiOiJkMGUwODdhNy1lZTAzLTQ3YjQtYWNhYy0wMDY1NDRmYzRhN2UiLCJhY3IiOiIwIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImxjbS1iYXNpYyIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1teWRvbWFpbiIsInVtYV9hdXRob3JpemF0aW9uIiwiYWMtY29uc3VtZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBtb250YmxhbmMgcHJvZmlsZSIsInNpZCI6ImQwZTA4N2E3LWVlMDMtNDdiNC1hY2FjLTAwNjU0NGZjNGE3ZSIsImh0dHA6Ly9teWRvbWFpbi5jb20vb3JnYW5pemF0aW9uX2lkIjoiNDkxYTZlYjctOWVhMS00NjEyLWE5YjMtYmZhMjU5MzViYmIyIiwiaHR0cDovL215ZG9tYWluLmNvbS9waG9uZV9udW1iZXIiOiIiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IkZpcnN0IExhc3QiLCJodHRwOi8vbXlkb21haW4uY29tL3VzZXJfcm9sZXMiOlsibGNtLWJhc2ljIiwib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLW15ZG9tYWluIiwidW1hX2F1dGhvcml6YXRpb24iLCJhYy1jb25zdW1lciJdLCJodHRwOi8vbXlkb21haW4uY29tL2VtYWlsIjoic29tZS51c2VyQHRoaXJkcGFydHkuY29tIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic29tZS51c2VyQHRoaXJkcGFydHkuY29tIiwiZ2l2ZW5fbmFtZSI6IkZpcnN0IiwiYWx0LXN1YiI6IjEwMDAwMCIsImZhbWlseV9uYW1lIjoiTGFzdCIsImVtYWlsIjoic29tZS51c2VyQHRoaXJkcGFydHkuY29tIiwiaHR0cDovL215ZG9tYWluLmNvbS9wYXJlbnRfdG9rZW4iOiJhYjNlNWQ1ZjNiZjFhZDJhMjA4MGU3NzE1YjRlMDVlMzIwMjIwMTIwMTYzMDAwMzE3In0.LncH7RYykWO--k-iqOwTjmn3mFnjMeY_ynsFa8UsIbf0ew76P_IM-Qdjz6mCAz02P23B9cXKy4ufUvkISqZT4THPjblzbAKE0w4_i40FIYFs5Qt5GKFt8o_o6R32Fa67HkvEVoH4W5pdZoeuQ8ID5Awq-NdXl2N5KXQzfoWfOwdYQD4rS98QxELcote9j3ewJkJTupAwMwvAwrtt5hWWhWfZFx7skzBl8RL4NNIluhciMJMGfZt1WnWPWXD8p-e8jwlCWSXpjodTRul5WgqH2uCiw8ePnB0VEa8Fqh2cPcTSKULieEQ_ryDzwW-fB26FeqBLvmYRTJXIJmtqjM8DKw
```

To see on [jwt.io](https://jwt.io/), click [here](https://jwt.io/#debugger-io?token=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwWDFnb1ctaU9HS3VTUzhVVU5SbnBKMUItWFVzcm44WmU0M1ZndTdmUlVjIn0.eyJleHAiOjE2NDU0NzEwOTIsImlhdCI6MTY0NTQ3MDE5MiwiYXV0aF90aW1lIjoxNjQ1NDcwMTQzLCJqdGkiOiJmMzI1MzYzNy00MmRmLTQ1MmYtYmYzNy0yMDBmYTVmOTlkMzkiLCJpc3MiOiJodHRwOi8va2V5Y2xvYWs6OTkwMC9hdXRoL3JlYWxtcy9teWRvbWFpbiIsImF1ZCI6WyJodHRwczovL2Rldi5teWRvbWFpbi5jb20iLCJodHRwOi8vZGV2LnRoaXJkLXBhcnR5LmNvbS9hcGkvdjIvIiwiYWNjb3VudCJdLCJzdWIiOiJzb21lLnVzZXJAdGhpcmRwYXJ0eS5jb20iLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJpYW0iLCJub25jZSI6ImEwZjc1Y2I3LTZmNzQtNGQ1NC1iMjk1LWMyNDhhYjNhZjZiYyIsInNlc3Npb25fc3RhdGUiOiJkMGUwODdhNy1lZTAzLTQ3YjQtYWNhYy0wMDY1NDRmYzRhN2UiLCJhY3IiOiIwIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImxjbS1iYXNpYyIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1teWRvbWFpbiIsInVtYV9hdXRob3JpemF0aW9uIiwiYWMtY29uc3VtZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBtb250YmxhbmMgcHJvZmlsZSIsInNpZCI6ImQwZTA4N2E3LWVlMDMtNDdiNC1hY2FjLTAwNjU0NGZjNGE3ZSIsImh0dHA6Ly9teWRvbWFpbi5jb20vb3JnYW5pemF0aW9uX2lkIjoiNDkxYTZlYjctOWVhMS00NjEyLWE5YjMtYmZhMjU5MzViYmIyIiwiaHR0cDovL215ZG9tYWluLmNvbS9waG9uZV9udW1iZXIiOiIiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IkZpcnN0IExhc3QiLCJodHRwOi8vbXlkb21haW4uY29tL3VzZXJfcm9sZXMiOlsibGNtLWJhc2ljIiwib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLW15ZG9tYWluIiwidW1hX2F1dGhvcml6YXRpb24iLCJhYy1jb25zdW1lciJdLCJodHRwOi8vbXlkb21haW4uY29tL2VtYWlsIjoic29tZS51c2VyQHRoaXJkcGFydHkuY29tIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic29tZS51c2VyQHRoaXJkcGFydHkuY29tIiwiZ2l2ZW5fbmFtZSI6IkZpcnN0IiwiYWx0LXN1YiI6IjEwMDAwMCIsImZhbWlseV9uYW1lIjoiTGFzdCIsImVtYWlsIjoic29tZS51c2VyQHRoaXJkcGFydHkuY29tIiwiaHR0cDovL215ZG9tYWluLmNvbS9wYXJlbnRfdG9rZW4iOiJhYjNlNWQ1ZjNiZjFhZDJhMjA4MGU3NzE1YjRlMDVlMzIwMjIwMTIwMTYzMDAwMzE3In0.LncH7RYykWO--k-iqOwTjmn3mFnjMeY_ynsFa8UsIbf0ew76P_IM-Qdjz6mCAz02P23B9cXKy4ufUvkISqZT4THPjblzbAKE0w4_i40FIYFs5Qt5GKFt8o_o6R32Fa67HkvEVoH4W5pdZoeuQ8ID5Awq-NdXl2N5KXQzfoWfOwdYQD4rS98QxELcote9j3ewJkJTupAwMwvAwrtt5hWWhWfZFx7skzBl8RL4NNIluhciMJMGfZt1WnWPWXD8p-e8jwlCWSXpjodTRul5WgqH2uCiw8ePnB0VEa8Fqh2cPcTSKULieEQ_ryDzwW-fB26FeqBLvmYRTJXIJmtqjM8DKw)
