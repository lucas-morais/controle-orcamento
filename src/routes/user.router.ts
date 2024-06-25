import { Router } from 'express';
import { UserController } from '../controller/user.controller';
import { AppDataSource } from '../data-source';
import { UserRepository } from '../repository/user.repository';
import { UserService } from '../service/user.service';

const repository = new UserRepository(AppDataSource);
const service = new UserService(repository);
const controller = new UserController(service);

const userRouter = Router();

userRouter.get('/', controller.getAllUsers);
userRouter.post('/', controller.createUser);
userRouter.get('/:id', controller.getUserById);
userRouter.patch('/:id', controller.updateUser);
userRouter.delete('/:id', controller.deleteUser);

export { userRouter };
