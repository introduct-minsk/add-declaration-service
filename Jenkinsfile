pipeline {
  environment {
    // add '/' at the end of dockerRegistry and dockerRegistryPush line if custom registry is used
    dockerRegistry = "harbor.riaint.ee/"
    // dockerRegistryPush is usually is the same as dockerRegistry but might differ
    dockerTag = "latest"
    dockerImage = rh3/add-declaration-service
  }
  agent any
  stages {
    stage('build declaration services'){
      agent {
        docker {
          image 'openjdk:11.0.5-jdk'
          args '-v $HOME/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock'
        }
      }
      steps {
        sh './mvnw -B clean package --projects :add-declaration-service --also-make'
        stash includes: 'services/*/target/*.jar', name: 'targetfiles'
      }
      post {
        always {
          archive "services/*/target/**/*"
          junit 'services/*/target/surefire-reports/*.xml'
        }
      }
    }
    stage('Building image') {
      agent { label 'master' }
      steps {
        unstash 'targetfiles'
        sh 'docker build --network host --build-arg JAR_FILE=services/add-declaration-service/target/add-declaration-service.jar -t "${dockerRegistryPush}${dockerImage}:${dockerTag}" .'
        sh 'docker push "${dockerRegistryPush}${dockerImage}:${dockerTag}"'
      }
    }
    stage('deploy') {
      // Put destination agent where the deployment is done
      agent { label 'master' }
      steps {
        sh 'docker-compose pull add-declaration-service'
        sh 'docker-compose -p consent-service up -d --scale add-declaration-service=2'
      }
    }
  }
}
