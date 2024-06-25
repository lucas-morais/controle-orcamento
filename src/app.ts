import express from 'express';
import { errorMiddleware } from './middleware/erro.middleware';
import { router } from './routes/router';

const app = express();

app.use(express.json());
app.use(router);
app.use(errorMiddleware);

export { app };
