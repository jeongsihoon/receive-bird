package jeong.sihoon.receive.bird.group.repository.data.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="chat_group_participant")
@IdClass(ChatGroupParticipant.GroupParticipantPK.class)
public class ChatGroupParticipant {
    @Data
    public static class GroupParticipantPK implements Serializable {
        private long groupId;
        private long userId;
    }

    @Id @Column(name = "group_id")
    private long groupId;

    @Id @Column(name = "user_id")
    private long userId;

}
