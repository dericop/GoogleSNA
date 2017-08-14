import requests
import json
import pickle

query='{ \
        organization(login:"google"){\
          repositories(first:25, orderBy:{field:STARGAZERS, direction:DESC}){\
            nodes{\
              name,\
              stargazers{totalCount},\
              primaryLanguage{name},\
              mentionableUsers(first:100){\
                nodes{\
                  name,\
                  login,\
                  company\
                }\
              }\
            }\
          }\
        }\
      }'
headers = {'Authorization': 'token e06ed368f9f33e17a713062b1da5ed83b018b1b4'}

r2=requests.post('https://api.github.com/graphql', json.dumps({"query": query}), headers=headers)

with open("request_result.file", "wb") as f:
  pickle.dump(r2, f, pickle.HIGHEST_PROTOCOL)