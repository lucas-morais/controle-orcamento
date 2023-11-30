import { Column, CreateDateColumn, Entity, PrimaryGeneratedColumn, UpdateDateColumn } from "typeorm";

@Entity()
export class User {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column({type: 'varchar', length: 100})
  nome: string;

  @Column({type: 'varchar', length: 100, unique: true})
  email: string;

  @Column()
  senha: string;

  @CreateDateColumn({name:'data_criacao'})
  dataCriacao: Date;

  @UpdateDateColumn({name: 'data_atualizacao'})
  dataAtualizacao: Date;

  @Column({name: 'ultimo_login', default: Date.now})
  ultimoLogin: Date;

}