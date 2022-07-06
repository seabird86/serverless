## AWS Services:
+ Lambda Function
+ IAM : Create a user with secret Key and role to deploy from console to cloud
+ CloudFormation > Stacks : view stack
+ S3: When deploy, it will automatically create a Bucket to store 'template file' and 'built image'


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

- Edit file .aws>config

```
[default]
region = us-east-1
```

- Run local:
  * $>sam build
  * $>sam validate
  * $>sam local invoke CreateCustomerFunction --event events/event.json
- Run Cloud:
  * $>aws lambda invoke --invocation-type RequestResponse --function-name {functionName} outputfile.txt


Note: Project 'common' and 'createCustomerFunction' need to configure the same version java 11, because AWS only support java 11

```
<properties>
  <maven.compiler.source>11</maven.compiler.source>
  <maven.compiler.target>11</maven.compiler.target>
</properties>
```
