import { MigrationInterface, QueryRunner } from "typeorm";

export class CriaTabelaUsuario1701350990045 implements MigrationInterface {
    name = 'CriaTabelaUsuario1701350990045'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`CREATE TABLE "usuario" ("id" uuid NOT NULL DEFAULT uuid_generate_v4(), "nome" character varying(100) NOT NULL, "email" character varying(100) NOT NULL, "senha" character varying NOT NULL, "data_criacao" TIMESTAMP NOT NULL DEFAULT now(), "data_atualizacao" TIMESTAMP NOT NULL DEFAULT now(), "ultimo_login" TIMESTAMP NOT NULL DEFAULT now(), CONSTRAINT "UQ_2863682842e688ca198eb25c124" UNIQUE ("email"), CONSTRAINT "PK_a56c58e5cabaa04fb2c98d2d7e2" PRIMARY KEY ("id"))`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`DROP TABLE "usuario"`);
    }

}
