import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class User {

    @PrimaryGeneratedColumn('uuid')
    id: string;

    @Column({length: 50, nullable: false})
    firstName: string;

    @Column({length: 50})
    lastName: string;

    @Column({length: 100, nullable: false, unique: true})
    email: string;

    @Column({nullable: false})
    password: string;

    @Column({length: 11, nullable: true})
    cpf: string;

}
