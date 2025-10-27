FROM golang:1.25-alpine

WORKDIR /app

RUN go install github.com/air-verse/air@latest

COPY go.mod ./

RUN go mod download
RUN go mod tidy

COPY . .

EXPOSE 3000

CMD ["sh", "-c", "go mod tidy && air -c .air.toml"]