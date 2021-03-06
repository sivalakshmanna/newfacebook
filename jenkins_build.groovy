pipeline{
    agent any
    stages{
        stage("chekout code"){
            steps{
                println "clone our code to our repository"
                sh "ls -l"
                sh "ls -lart ./*"
                git branch: "siva",
                url: 'https://github.com/KuruvaSomaSekhar/boxfuse-sample-java-war-hello.git'
                

            }
        }
        stage("build code"){
            steps{
                println "mvn clean package"
                sh "mvn clean package"
                sh "ls -l target/"
            }
        }
    }
}
