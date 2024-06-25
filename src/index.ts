import { config } from 'dotenv';
import { app } from './app';
import { AppDataSource } from './data-source';

config();
const PORT = parseInt(process.env.PORT) | 3000;

AppDataSource.initialize().then(() => {
  app.listen(PORT, () => {
    console.log(`App rodando na porta ${PORT}`);
  });
}).catch(error => {
  console.log('Servidor não operacional \n\n');
  console.warn(error);
});
