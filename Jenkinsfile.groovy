node('Agent-2') {
    
    stage('Checkout') {
        checkout([$class: 'GitSCM',
                  branches: [[name: 'main']],
                  userRemoteConfigs: [[url: 'https://github.com/Veena-devops/java-example.git']]
                 ])
    }

    stage('Test') {
        try {
            sh 'mvn clean test'
            echo 'Running unit test and integration test'
        } catch (Exception e) {
            error 'Tests failed!'
        }
    }

    stage('Build') {
        try {
            sh 'mvn clean package'
            echo 'Build successful'
        } catch (Exception e) {
            error 'Build failed!'
        }
    }

    stage('Deploy') {
        try {
            sh 'sudo cp /home/ubuntu/jenkins/workspace/ques4-tomcat-declarative/target/works-with-heroku-1.0.war /opt/apache-tomcat-9.0.95/webapps'
            sh 'sudo stopTomcat'
            sh 'sudo startTomcat'
            echo 'Successfully deployed'
        } catch (Exception e) {
            error 'Deployment failed!'
        }
    }
}
