pipeline {
    agent any
    environment {
        DOCKER_REGISTRY = 'erp-registry.company.com'
        KUBERNETES_NAMESPACE = 'erp-production'
    }
    stages {
        stage('Build') {
            steps { sh 'mvn clean package' }
        }
        stage('Test') {
            steps { sh 'mvn test' }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${DOCKER_REGISTRY}/erp-system:${BUILD_NUMBER} .'
            }
        }
    }
}
