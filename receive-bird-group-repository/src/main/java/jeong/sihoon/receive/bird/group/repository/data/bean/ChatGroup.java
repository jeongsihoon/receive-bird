package jeong.sihoon.receive.bird.group.repository.data.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="chat_group")
public class ChatGroup {
    @Id @Column(name="group_id")
    private long groupId;


    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name="group_id")
    protected Set<ChatGroupParticipant> groupParticipants = new HashSet<>();

}
