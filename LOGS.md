# Log

> NOTE:
> Open HAR files with <https://toolbox.googleapps.com/apps/har_analyzer/>

## First login (no user exists before this)

### Keycloak Logs

```log
17:58:14,889 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProviderFactory] (default task-6) [I00] creating new CustomUserStorageProvider
17:58:14,930 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I03] getUserByEmail(email=some.user@thirdparty.com, realm=mydomain)
17:58:14,931 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I04] findUser(realm=mydomain, identifier=some.user@thirdparty.com)
17:58:14,931 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I05] getFromStorage(realm=mydomain, identifier=some.user@thirdparty.com)
17:58:14,931 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I05] Trying to find user [some.user@thirdparty.com] in userLocalStorage...
17:58:14,972 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I05] Users: []
17:58:14,974 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I04] no user in userLocalStorage...
17:58:14,975 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I04] creating initial UserModel...
17:58:14,975 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I06] addToStorage(realm=mydomain, identifier=some.user@thirdparty.com)
17:58:15,015 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I06] Setting Federation link and email status...
17:58:15,016 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I06] Model ID: 254c26a6-d976-4a5d-9ba7-fca8d1da38d2
17:58:15,054 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I07] supportsCredentialType(password)
17:58:15,056 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I09] isValid(realm=mydomain, user=id:7b446273-eb83-4778-8c11-468571c51936[email:some.user@thirdparty.com], credentialInput.type=password)
17:58:16,726 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I10] updateUserInfo(realm=mydomain, user=id:100000[email:some.user@thirdparty.com])
17:58:16,727 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I10] Setting attributes...
17:58:16,728 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I11] setUserAttributes(model=id:7b446273-eb83-4778-8c11-468571c51936[email:some.user@thirdparty.com], user=id:100000[email:some.user@thirdparty.com])
17:58:16,729 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I10] Setting roles...
17:58:16,730 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I12] getRoleModels(realm=mydomain, additionalInfo={ "actionableContent": true, "lcmBasic": true })
17:58:16,736 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I13] getRoleModel(realm=mydomain, role=ac-consumer)
17:58:16,763 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I13] getRoleModel(realm=mydomain, role=lcm-basic)
17:58:16,781 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I10] Users Cache: [ID: 7b446273-eb83-4778-8c11-468571c51936 - Email: some.user@thirdparty.com]
17:58:16,782 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I09] Cleaning session userCache...
17:58:16,798 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I09] User Validated? true
17:58:16,810 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I07] supportsCredentialType(otp)
17:58:17,424 INFO  [com.mydomain.auth.provider.user.CustomUserStorageProvider] (default task-6) [I99] close()

```

### Resulting Token

```
eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwWDFnb1ctaU9HS3VTUzhVVU5SbnBKMUItWFVzcm44WmU0M1ZndTdmUlVjIn0.eyJleHAiOjE2NDYyNDQ3OTcsImlhdCI6MTY0NjI0Mzg5NywiYXV0aF90aW1lIjoxNjQ2MjQzODk3LCJqdGkiOiJjZWMzM2Q0YS1iYzViLTQ4MjUtODVmZi0yZGRiMGFjOWY5NWMiLCJpc3MiOiJodHRwOi8va2V5Y2xvYWs6OTkwMC9hdXRoL3JlYWxtcy9teWRvbWFpbiIsImF1ZCI6WyJodHRwczovL2Rldi5teWRvbWFpbi5jb20iLCJodHRwOi8vZGV2LnRoaXJkLXBhcnR5LmNvbS9hcGkvdjIvIiwiYWNjb3VudCJdLCJzdWIiOiI3YjQ0NjI3My1lYjgzLTQ3NzgtOGMxMS00Njg1NzFjNTE5MzYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJpYW0iLCJub25jZSI6IjdlODBiZjhmLTc1ZWMtNGZjMi04ODQ2LTM2NTQxNzkzM2ZiNiIsInNlc3Npb25fc3RhdGUiOiI0MTlkMGEzMy1mNTE0LTQxMDgtYTZhYS1lZWQ3M2IyZDk4YzMiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImxjbS1iYXNpYyIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1teWRvbWFpbiIsInVtYV9hdXRob3JpemF0aW9uIiwiYWMtY29uc3VtZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBtb250YmxhbmMgcHJvZmlsZSIsInNpZCI6IjQxOWQwYTMzLWY1MTQtNDEwOC1hNmFhLWVlZDczYjJkOThjMyIsInN1YiI6IjEwMDAwMCIsImh0dHA6Ly9teWRvbWFpbi5jb20vb3JnYW5pemF0aW9uX2lkIjoiNDkxYTZlYjctOWVhMS00NjEyLWE5YjMtYmZhMjU5MzViYmIyIiwiaHR0cDovL215ZG9tYWluLmNvbS9waG9uZV9udW1iZXIiOiIiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IkZpcnN0IExhc3QiLCJodHRwOi8vbXlkb21haW4uY29tL3VzZXJfcm9sZXMiOlsibGNtLWJhc2ljIiwib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLW15ZG9tYWluIiwidW1hX2F1dGhvcml6YXRpb24iLCJhYy1jb25zdW1lciJdLCJodHRwOi8vbXlkb21haW4uY29tL2VtYWlsIjoic29tZS51c2VyQHRoaXJkcGFydHkuY29tIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic29tZS51c2VyQHRoaXJkcGFydHkuY29tIiwiZ2l2ZW5fbmFtZSI6IkZpcnN0IiwiZmFtaWx5X25hbWUiOiJMYXN0IiwiZW1haWwiOiJzb21lLnVzZXJAdGhpcmRwYXJ0eS5jb20iLCJodHRwOi8vbXlkb21haW4uY29tL3BhcmVudF90b2tlbiI6ImFiM2U1ZDVmM2JmMWFkMmEyMDgwZTc3MTViNGUwNWUzMjAyMjAxMjAxNjMwMDAzMTcifQ.Z1EE-dJDeccSDVDpe3ipBLztD3lK6d23JqidNsTRkQ-MDuocwz42oRuZnC3lBnGfBlzABVpJIo79m8jz2hlcV8aMa_MaajiOeFbzj6QdjFAV82rVNjh8HYdZmVDFxS2Sr8FYDs4q9Y-ix-5wnMZ13RkDGY4fCrXnUho91mds5huyBDUCJymE0Y4sf2Vs1N4MD8It33rItDWDU8oy-y-4WFI0osCZPf8n5C2o1auyzZ_WbORpBsjWB04UuvDfi7dbKtg65DqpO9ZXJ9pJqyIygn8sSSwghfL1YM9-lJUyaNQC4x8sKX-4fq5uLtmF8BDiakZSKKjAbmNxYDKQ9rGhUA
```

To see on [jwt.io](https://jwt.io/), click [here](https://jwt.io/#debugger-io?token=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwWDFnb1ctaU9HS3VTUzhVVU5SbnBKMUItWFVzcm44WmU0M1ZndTdmUlVjIn0.eyJleHAiOjE2NDYyNDQ3OTcsImlhdCI6MTY0NjI0Mzg5NywiYXV0aF90aW1lIjoxNjQ2MjQzODk3LCJqdGkiOiJjZWMzM2Q0YS1iYzViLTQ4MjUtODVmZi0yZGRiMGFjOWY5NWMiLCJpc3MiOiJodHRwOi8va2V5Y2xvYWs6OTkwMC9hdXRoL3JlYWxtcy9teWRvbWFpbiIsImF1ZCI6WyJodHRwczovL2Rldi5teWRvbWFpbi5jb20iLCJodHRwOi8vZGV2LnRoaXJkLXBhcnR5LmNvbS9hcGkvdjIvIiwiYWNjb3VudCJdLCJzdWIiOiI3YjQ0NjI3My1lYjgzLTQ3NzgtOGMxMS00Njg1NzFjNTE5MzYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJpYW0iLCJub25jZSI6IjdlODBiZjhmLTc1ZWMtNGZjMi04ODQ2LTM2NTQxNzkzM2ZiNiIsInNlc3Npb25fc3RhdGUiOiI0MTlkMGEzMy1mNTE0LTQxMDgtYTZhYS1lZWQ3M2IyZDk4YzMiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImxjbS1iYXNpYyIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1teWRvbWFpbiIsInVtYV9hdXRob3JpemF0aW9uIiwiYWMtY29uc3VtZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBtb250YmxhbmMgcHJvZmlsZSIsInNpZCI6IjQxOWQwYTMzLWY1MTQtNDEwOC1hNmFhLWVlZDczYjJkOThjMyIsInN1YiI6IjEwMDAwMCIsImh0dHA6Ly9teWRvbWFpbi5jb20vb3JnYW5pemF0aW9uX2lkIjoiNDkxYTZlYjctOWVhMS00NjEyLWE5YjMtYmZhMjU5MzViYmIyIiwiaHR0cDovL215ZG9tYWluLmNvbS9waG9uZV9udW1iZXIiOiIiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IkZpcnN0IExhc3QiLCJodHRwOi8vbXlkb21haW4uY29tL3VzZXJfcm9sZXMiOlsibGNtLWJhc2ljIiwib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLW15ZG9tYWluIiwidW1hX2F1dGhvcml6YXRpb24iLCJhYy1jb25zdW1lciJdLCJodHRwOi8vbXlkb21haW4uY29tL2VtYWlsIjoic29tZS51c2VyQHRoaXJkcGFydHkuY29tIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic29tZS51c2VyQHRoaXJkcGFydHkuY29tIiwiZ2l2ZW5fbmFtZSI6IkZpcnN0IiwiZmFtaWx5X25hbWUiOiJMYXN0IiwiZW1haWwiOiJzb21lLnVzZXJAdGhpcmRwYXJ0eS5jb20iLCJodHRwOi8vbXlkb21haW4uY29tL3BhcmVudF90b2tlbiI6ImFiM2U1ZDVmM2JmMWFkMmEyMDgwZTc3MTViNGUwNWUzMjAyMjAxMjAxNjMwMDAzMTcifQ.Z1EE-dJDeccSDVDpe3ipBLztD3lK6d23JqidNsTRkQ-MDuocwz42oRuZnC3lBnGfBlzABVpJIo79m8jz2hlcV8aMa_MaajiOeFbzj6QdjFAV82rVNjh8HYdZmVDFxS2Sr8FYDs4q9Y-ix-5wnMZ13RkDGY4fCrXnUho91mds5huyBDUCJymE0Y4sf2Vs1N4MD8It33rItDWDU8oy-y-4WFI0osCZPf8n5C2o1auyzZ_WbORpBsjWB04UuvDfi7dbKtg65DqpO9ZXJ9pJqyIygn8sSSwghfL1YM9-lJUyaNQC4x8sKX-4fq5uLtmF8BDiakZSKKjAbmNxYDKQ9rGhUA)
