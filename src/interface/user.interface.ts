export interface CreateUserDTO {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  cpf: string;
}

export interface UpdateUserDTO {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  cpf: string;
}

export interface UserDTO {
  id: string
  firstName: string;
  lastName: string;
  email: string;
}
