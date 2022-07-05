
- Login aws to create a user with access key and secret key:
  * Choose IAM -> users -> add user
  * Enter name of user -> next button
  * Choose permission : AdministrationAccess

- go to console to configure aws cli:
  * $>aws configure
  * enter access key ID XXXX and secret Access Key YYYY
- Create new Sam Project
  * sam init -r java11 -d maven --app-template createCustomer -n createCustomer
  * go to project then $>sam build then $>sam deploy --guided

- Go to aws > CloudFormation


- Edit file .aws>config

```
[default]
region = us-east-1
```

- Validate template.yaml file by:
  * $>sam validate

- Run local
  * $>sam local invoke CreateCustomerFunction --event events/event.json


  sam local invoke CreateCustomerFunction --event events/event.json

  sam local invoke HelloWorldFunction --event events/event.json

- project 'common' and 'createCustomerFunction' has the same version

```
<properties>
  <maven.compiler.source>11</maven.compiler.source>
  <maven.compiler.target>11</maven.compiler.target>
</properties>
```

- aws lambda invoke --invocation-type RequestResponse --function-name customer-CreateCustomerFunction-id66TDWmWoBf outputfile.txt
