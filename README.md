# receive-bird
메신저 백엔드 개발 프로젝트
https://docs.google.com/presentation/d/17iYsghZAGymy6XweNlnzGIbAv6fidXJdjdJ9a66xJWs/edit?usp=sharing

프로젝트 이름은 sendbird에 대한 오마쥬

1. 메시지 핸들러 서비스
  클라이언트와 연결되는 서버
  메시지 송수신 역할
  
2. 메시지 핸들러 매니저 서비스
  클라이언트가 메시지 핸들러 서비스의 
  어떤 노드에 연결되었는지 관리하는 서비스
  
3. 유저 정보 관리 서비스
  유저별 정보 관리 - 프로필, 차단 여부 등
  
4. 그룹 정보 관리 서비스
  그룹별 정보 관리 - 단체방의 참여 유저 관리 등


서비스간 통신은 Kafka

클라이언트와 메시지 핸들러 서비스간 메시지 송수신은 직렬화된 메시지(protobuf)

유저 정보 관리 서비스나 그룹 정보 관리 서비스는 REST API


