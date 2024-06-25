import { NextFunction, Request, Response } from 'express';
import { CreateUserDTO, UpdateUserDTO } from './interface/user.interface';
import { UserService } from './service/user.service';

export class UserController {
  constructor(private _service: UserService) {}

  async createUser(req: Request, res: Response, next: NextFunction) {
    const createUserDTO: CreateUserDTO = req.body;
    try {
      const userCreated = await this._service.createUser(createUserDTO);
      return res.status(201).json(userCreated);
    } catch(err) {
      next(err);
    }
  }

  async getAllUsers(req: Request, res: Response, next: NextFunction) {
    try {
      const users = await this._service.getAllUsers();
      return res.status(200).json(users);
    } catch(err) {
      next(err);
    }
  }

  async getUserById(req: Request, res: Response, next: NextFunction) {
    const id: string = req.params.id;
    try {
      const user = await this._service.getUserById(id);
      return res.status(200).json(user);
    } catch(err) {
      next(err);
    }
  }

  async updateUser(req: Request, res: Response, next: NextFunction) {
    const id = req.params.id;
    const userUpdateDTO: UpdateUserDTO = req.body;
    try {
      await this._service.updateUser(id, userUpdateDTO);
      return res.status(204).send();
    } catch(err) {
      next(err);
    }
  }

  async deleteUser(req: Request, res: Response, next: NextFunction) {
    const id = req.params.id;
    try {
      await this._service.deleteUser(id);
      return res.status(204).send();
    } catch(err) {
      next(err);
    }
  }
}