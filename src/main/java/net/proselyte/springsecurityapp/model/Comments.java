package net.proselyte.springsecurityapp.model;

import javax.persistence.*;

    @Entity
    @Table(name = "comments")
    public class Comments {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "commentsId")
        private Long id;
        @Column(name = "content")
        private String content;
        @Column(name = "instructions_id")
        private Long instructionId;
        @Column(name = "owner_id")
        private int ownerId;
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Long getInstructionId() {
            return instructionId;
        }

        public void setInstructionId(Long instructionId) {
            this.instructionId = instructionId;
        }

        public int getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(int ownerId) {
            this.ownerId = ownerId;
        }


}