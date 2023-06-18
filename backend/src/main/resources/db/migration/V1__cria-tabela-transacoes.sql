CREATE TABLE transacoes (
    id SERIAL PRIMARY KEY,
    valor NUMERIC(10, 2) NOT NULL,
    descricao TEXT NOT NULL,
    data DATE NOT NULL,
    tipo_transacao VARCHAR(1) NOT NULL
);