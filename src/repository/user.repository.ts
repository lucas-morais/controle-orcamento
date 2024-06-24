import { User } from 'src/entity/User';
import { CreateUserDTO, UpdateUserDTO } from 'src/interface/user.interface';
import { DataSource, Repository, } from 'typeorm';

export class UserRepository {
  private _repository: Repository<User>;

  constructor(private _datasource: DataSource) {
    this._repository = this._datasource.getRepository(User);
  }

  async createUser(user: CreateUserDTO): Promise<User> {
    const userCreated = this._repository.create(user);
    await this._repository.save(userCreated);
    return userCreated;
  }

  async getAllUsers(): Promise<User[]> {
    const users = await this._repository.find();
    return users;
  }

  async getUserById(id: string): Promise<User> {
    const user = await this._repository.findOneBy({id});
    return user;
  }

  async updateUser(id: string, updateUserDTO: UpdateUserDTO): Promise<void> {
    await this._repository.update({id}, updateUserDTO);
  }

  async deleteUser(id: string): Promise<void> {
    await this._repository.delete({id});
  }
}