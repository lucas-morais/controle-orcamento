import { CreateUserDTO, UpdateUserDTO, UserDTO } from 'src/interface/user.interface';
import { UserRepository } from 'src/repository/user.repository';

export class UserService {
  constructor(private _repository: UserRepository) {

  }

  async createUser(user: CreateUserDTO): Promise<UserDTO> {
    const userCreated = await this._repository.createUser(user);
    return {
      id: userCreated.id,
      email: userCreated.email,
      firstName: userCreated.firstName,
      lastName: userCreated.lastName
    };
  }

  async getAllUsers(): Promise<UserDTO[]> {
    const users = await this._repository.getAllUsers();
    return users.map((user) => ({
      id: user.id,
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName
    }));
  }

  async getUserById(id: string): Promise<UserDTO> {
    const user = await this._repository.getUserById(id);
    return {
      id: user.id,
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName,
    };
  }

  async updateUser(id: string, updateUserDTO: UpdateUserDTO) {
    await this._repository.updateUser(id, updateUserDTO);
  }

  async deleteUser(id: string) {
    await this._repository.deleteUser(id);
  }
}