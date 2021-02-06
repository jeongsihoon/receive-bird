package jeong.sihoon.receive.bird.group.repository.jpa.repository;

import jeong.sihoon.receive.bird.group.repository.data.bean.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatGroupRepository extends JpaRepository<ChatGroup, Long> {
}
