package com.bsuirnethub.entity

import com.bsuirnethub.alias.TeacherId
import jakarta.persistence.*

@Entity
@Table(
    name = "user_teachers",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_teacher_user",
            columnNames = ["teacher_id", "user_id"]
        )
    ]
)
class TeacherEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "teacher_id")
    var teacherId: TeacherId? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null
)
