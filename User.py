import os
import sys
from datetime import datetime
from sqlalchemy import Column, ForeignKey, Integer, String, DateTime, Float
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from sqlalchemy import Table, create_engine
from sqlalchemy.orm import sessionmaker
from decimal import Decimal, getcontext
from pathlib import Path

Base = declarative_base()
#User = Table('user', Base.metadata,Column('user_id', Integer, primary_key=True))
#Class General User
class User(Base):
    __tablename__ = 'user'
    # Here we define columns for the table user
    # Notice that each column is also a normal Python instance attribute.
    id = Column(Integer, primary_key=True)
    mail = Column(String(250), nullable=False)
    password = Column(String(250),nullable = False)

# Create an engine that stores data in the local directory's
# sqlalchemy_example.db file.
engine = create_engine('sqlite:///Phonograph.db')

# Create all tables in the engine. This is equivalent to "Create Table"
# statements in raw SQL.
Base.metadata.create_all(engine)
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()

def add_user(mail,password):
    user = User(mail=mail, password=password)
    session.add(user)
    session.commit()

def view_user():
    Allusers = session.query(User).all()
    if(Allusers != None):
        print()
        for everyuser in Allusers:
            print(f'* {everyuser.mail}')

def delete_user(mail,password):
    s = session.query(User).filter(User.mail == mail).first()
    session.delete(s)
    session.commit()

#add_user('mariam@yahoo.com' , 'marwa')
view_user()
#delete_user('marwa@yahoo.com','marwa')