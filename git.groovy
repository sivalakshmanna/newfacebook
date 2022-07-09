pipeline{
    agent any
    stages{
        stage('clone the code'){
            steps{
                println ("hello world")
            }
        }
    }
}