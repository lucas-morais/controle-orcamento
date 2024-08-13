import { randomUUID } from 'crypto';
import { DataSource } from 'typeorm';
import { User } from '../../../src/entity/User';
import { CreateUserDTO } from '../../../src/interface/user.interface';
import { UserRepository } from '../../../src/repository/user.repository';
import { mockDataSource, mockRepository } from '../../mocks/database.mock';

describe('Testing User Repository Layer', () => {
  let userRepository: UserRepository

  beforeAll(() => {
    userRepository = new UserRepository(mockDataSource as unknown as DataSource);
  })

  afterEach(() => {
    jest.clearAllMocks();
  })

  describe('Testing user create method', () => {
    it('Should return a user if data is provided correctly', async () => {
      const createUserDTO: CreateUserDTO = {
        firstName: 'Ringo',
        lastName: 'Starr',
        cpf: '12345678910',
        email: 'email@gmail.com',
        password: '123456789',
      }
      const userCreated: User = {
        id: randomUUID(),
        firstName: 'Ringo',
        lastName: 'Starr',
        cpf: '12345678910',
        email: 'email@gmail.com',
        password: '123456789',
      };


      mockRepository.create.mockReturnValue(userCreated);
      mockRepository.save.mockReturnThis();

      const user = await userRepository.createUser(createUserDTO);

      expect(mockRepository.create).toHaveBeenCalledWith(createUserDTO);
      expect(mockRepository.save).toHaveBeenCalledWith(userCreated);
      console.log(user)
      expect(user).toEqual(userCreated);

    })
  });


})