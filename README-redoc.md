# ReDoc documentation

#### Setup

Install npm: <br/>
`# apt install nodejs npm`

Check node version if it's less than 12 version, than need to install latest:
~~~~
# curl -sL https://deb.nodesource.com/setup_14.x -o nodesource_setup.sh 
# sudo bash nodesource_setup.sh
# apt install nodejs
# node -v
~~~~

Install ReDoc cli <br/>
`# npm install -g redoc-cli`

#### Run
ReDoc require to have running Swagger. It consumes api-doc created by Swagger to expose it's own UI.

For example if application with Swagger is running on `8683` port and we want to run ReDoc on npm lightweight server on port `8684` execution command will be:

~~~~
# redoc-cli serve -p 8684 http://localhost:8683/v3/api-docs
~~~~
  
