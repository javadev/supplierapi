# Data Base Information

###### Setup information and notes. As well as data base related hints and commands.

#### Setup

Main config to **open connection** and **change port**:
`/etc/postgresql/10/main/postgresql.conf`

Changed:
~~~~
listen_addresses = '162.55.164.107,localhost'
port = 7325
~~~~

Host-based authentication file:

`/etc/postgresql/10/main/pg_hba.conf`

Added:
~~~~
host    all             all             116.202.15.234/32       md5
host    all             all             162.55.164.107/32       md5
~~~~

#### Status and restart

Postgres status
$ systemctl status postgresql

Postgres status start/stop/restart
$ sudo systemctl stop postgresql
$ sudo systemctl start postgresql
$ sudo systemctl restart postgresql

#### Console

On a server to run postgres console:

`$ sudo -u postgres psql` <br/>
or <br/>
`$ sudo -i -u postgres`<br/>
then <br/>
`$ psql`

Exit from console:<br/>
`postgres=# \q`

List all user accounts (or roles) in the current PostgreSQL:<br/>
`postgres=# \du`

More information:<br/>
`postgres=#\du+`

Show databases:<br/>
`postgres=# \l`

More information:<br/>
`postgres=# \l+`

#### SQL Examples:

Create Role:
~~~~
CREATE ROLE roomdb WITH  
    LOGIN  
    NOSUPERUSER  
    INHERIT  
    NOCREATEDB  
    NOCREATEROLE  
    REPLICATION  
    PASSWORD 'some_password_here';
~~~~

##### Create Database:
~~~~
CREATE DATABASE room_db
    WITH 
    OWNER = roomdb
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1; 
~~~~

