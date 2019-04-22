import os
import sys
from datetime import datetime
from sqlalchemy import Column, ForeignKey, Integer, String, DateTime, Float , Boolean
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from sqlalchemy import Table, create_engine
from sqlalchemy.orm import sessionmaker
from decimal import Decimal, getcontext
from pathlib import Path

import Restaurant
import table

Base = declarative_base()

class Branch(Base):
    __tablename__ = 'branch'
    # Here we define columns for the table Artist.
    # Notice that each column is also a normal Python instance attribute.
    id = Column(Integer, primary_key=True)
    address = Column(String(250), nullable=False)
    numOfTables = Column(Integer(precision=50))
    delivery = Column(Boolean(250), nullable=False)
    # relations
    tables = relationship(Table , backref='tables', lazy='dynamic')




# Create an engine that stores data in the local directory's
# sqlalchemy_example.db file.
engine = create_engine('sqlite:///Phonograph.db')

# Create all tables in the engine. This is equivalent to "Create Table"
# statements in raw SQL.
Base.metadata.create_all(engine)
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()

def add_branch(address,restaurantName,numofTables,tables,delivery):
    branch = Branch(address=address, restaurantName = restaurantName,numofTables = numofTables,tables=tables,delivery=delivery)
    session.add(branch)
    session.commit()

def view_branch():
    Allbranches = session.query(Branch).all()
    if(Allbranches != None):
        print()
        for everybranch in Allbranches:
            print(f'* {everybranch.mail}')

def delete_branch(address,restaurantName):
    s = session.query(Branch).filter(Branch.address == address).first()
    session.delete(s)
    session.commit()

add_branch('123 cairo','Mac',15,True)
view_branch()
#delete_branch(123 cairo','Mac')
