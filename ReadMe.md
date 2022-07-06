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


```
sam local invoke CreateCustomerFunction --event events/event-create.json
sam local invoke GetCustomerFunction --event events/event-get.json
```


aws apigateway get-resources --rest-api-id 691717483856:CreateCustomerFunction --region us-east-1 --http-method POST --request-parameters method.request.path.id=28e9b440-a997-4a15-89c6-f3e3570be9d3
 --authorization-type AWS_IAM
