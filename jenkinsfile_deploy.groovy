pipeline{
    agent any 
    parameters{
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'give branch name')
        string(name: 'SERVER_IP', defaultValue: '', description: 'give serverip address')
        string(name: 'BUILD_NUMBER', defaultValue: '', description: 'give build number')
    }
    stages{
        stage("download artifacts"){
            steps{
                println "download artifacts from s3"
                sh """
                       aws s3 ls s3://sivabandela
                       aws s3 cp s3://sivabandela/hello-${BUILD_NUMBER}.war .
                   """
            }
        }
        stage("copy artifacts"){
            steps{
                println "copying artifacts"
                //sh "ssh -i /tmp/sivalakshmanna07.pem ec2-user@${SERVER_IP}" 
                sh "scp  -o StrictHostKeyChecking=no -i /tmp/sivalakshmanna07.pem hello-${BUILD_NUMBER}.war ec2-user@${SERVER_IP}:/tmp/"
                sh """
                 inputArray=$SERVER_IP
                  echo $inputArray
                   
                 IFS=',' read -r -a outputArray <<< "$inputArray"
                  for ip in ${outputArray[SERVER_IP]}
                  do
                        echo "deploying code to : $ip"
                  
                  
                 "ssh  -o StrictHostKeyChecking=no -i /tmp/sivalakshmanna07.pem ec2-user@${SERVER_IP} \"sudo cp /tmp/hello-${BUILD_NUMBER}.war /var/lib/tomcat/webapps\""
                done
                   """
            }
        }
    }
}