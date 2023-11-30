import { config } from 'dotenv';
import { DataSource } from 'typeorm';

config();

const AppDatasource = new DataSource({
  type: 'postgres',
	host: process.env.DB_HOST,
	port: parseInt(process.env.DB_PORT || '5432'),
	username: process.env.DB_USER,
	password: process.env.DB_PASSWORD,
	database: process.env.DB_NAME,
	entities: ['./src/model/**/*.ts'],
	migrations: ['./src/database/migrations/*.ts'],
});

AppDatasource.initialize()
	.then(() => { console.log('Data source inicializado com sucesso'); })
	.catch((err) => { console.error('Houve erro na inicialização do Data Source', err); });

export default AppDatasource;