 pipeline{
    agent any 
    parameters{
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'give branch name')
        string(name: 'BRANCH_PIPE', defaultValue: 'master', description: 'give branch name')
        //string(name: 'SOURCE_CODE', defaultValue: 'https://github.com/sivalakshmanna/boxfuse-sample-java-war-hello.git', description: 'give branch name')
        //string(name: 'POM', defaultValue: '', description: 'give branch name')
    }
    stages{
        stage("clone the code"){
            steps{
                println "clonig the code from git hub"
            }
        }
        stage("build the code"){
            steps{
                println "build the code"
                sh "mvn clean package"
                sh "ls -l"
            }
        }
        stage("upload artifacts to s3"){
            steps{
                println "upload artifacts to s3 bucket"
                sh "echo $BUILD_NUMBER"
                sh "aws s3 cp target/hello-${BUILD_NUMBER}.war s3://sivabandela/"
            }
        }
    }
}