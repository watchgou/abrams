pipeline {
    agent any
    environment {
       project_name = "abrams"
       docker_tag = "latest"
    }

    stages {
        stage('环境检测') {
            steps {
                sh '''
                    gradle -v
                    git version
                    java -version
                    docker --version
                '''
            }
        }
        stage('项目构建') {
            steps {
                sh '''
                     gradle clean build
                '''
            }
        }
		stage('删除镜像容器') {
			steps {
                sh '''
                   if [ $(docker ps -q -f "name=^${project_name}$") ];then
                        docker stop ${project_name}
                   	    docker rm ${project_name}
                   else
                   	    echo "No  docker container running ${project_name}"
                   fi

                   if [ $(docker ps -a -q -f "name=^${project_name}$") ];then
                        docker rm ${project_name}
                  else
                        echo "No  docker container  ${project_name}"
                    fi

                   if [ $(docker images -q jon110/${project_name}:${docker_tag}) ];then
                        docker rmi jon110/${project_name}:${docker_tag}
                  else
                        echo "The image does not exist  ${project_name}:${docker_tag}"
                   fi

                   if [ $(docker images -q ${project_name}:${docker_tag}) ];then
                        docker rmi ${project_name}:${docker_tag}
                  else
                        echo "The image does not exist  ${project_name}:${docker_tag}"
                   fi
                 '''
			}
		}
		stage('构建镜像') {
			steps {
                sh '''
                    docker build --rm -t ${project_name}:${docker_tag} .
                 '''
			}
		}
		stage('发布镜像') {
            steps {
                sh '''
                    if [ $(docker images |grep ${project_name} |awk '{print $3}') ];then
                        docker tag $(docker images |grep ${project_name} |awk '{print $3}') jon110/${project_name}:${docker_tag}
                        docker push jon110/${project_name}:${docker_tag}
                        docker rmi jon110/${project_name}:${docker_tag}
                        docker rmi ${project_name}:${docker_tag}
                    fi
                    docker system prune -f
                 '''
            }
        }
		stage('项目部署') {
			steps {
                sh '''
                  docker pull jon110/${project_name}:${docker_tag}
                  docker run -p 7080:7080 --name ${project_name} -v /var/log/${project_name}:/server-java/logs --restart=always -d jon110/${project_name}:${docker_tag}
                 '''
			}
		}
	}
    post {
        always {
         deleteDir()
        }
    }
