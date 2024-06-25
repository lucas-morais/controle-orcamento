import { NextFunction, Request, Response } from 'express';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
function errorMiddleware(err: Error, req: Request, res: Response, next: NextFunction) {
  console.warn(err);
  res.status(500).json({message: 'Something broke!'});
}

export { errorMiddleware };
