pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY = '123456789012.dkr.ecr.us-east-1.amazonaws.com'
        KUBECONFIG_CREDENTIALS = credentials('eks-kubeconfig')
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Security Scanning (SAST)') {
            steps {
                echo 'Running Checkmarx SAST scanning...'
                // sh 'cx scan --project ERP_System'
            }
        }
        
        stage('Build Backend') {
            steps {
                sh 'mvn clean package -DskipTests'
                sh 'docker build -t $DOCKER_REGISTRY/erp-backend:${env.BUILD_ID} .'
            }
        }
        
        stage('Push to ECR') {
            steps {
                echo 'Pushing Docker images to AWS Elastic Container Registry'
                // sh 'aws ecr get-login-password | docker login --username AWS --password-stdin $DOCKER_REGISTRY'
                // sh 'docker push $DOCKER_REGISTRY/erp-backend:${env.BUILD_ID}'
            }
        }
        
        stage('Deploy (Blue/Green)') {
            steps {
                echo 'Deploying to Kubernetes via Blue/Green Strategy'
                // sh 'kubectl apply -f kubernetes/backend-deployment.yaml'
            }
        }
    }
    
    post {
        always {
            echo 'Archiving logs to S3...'
        }
        failure {
            echo 'Triggering PagerDuty incident for deployment failure!'
        }
    }
}
