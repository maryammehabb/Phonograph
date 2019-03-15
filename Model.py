import os
import sys
from datetime import datetime
from sqlalchemy import Column, ForeignKey, Integer, String, DateTime, Float
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from sqlalchemy import Table, create_engine
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from decimal import Decimal, getcontext
from pathlib import Path

Base = declarative_base()


class Restaurant(Base):
    __tablename__ = 'restaurant'
    ID = Column(Integer, primary_key=True)
    name = Column(String(50))
    '''
    branchs = Column([])

    locations = Column()
    openTime = Column()
    closeTime = Column()'''


class Complain(Base):
    __tablename__ = 'complain'
    ID = Column(Integer, primary_key=True)
    name = Column(Integer)
    # branch_ID = Column(Integer, ForeignKey('branch.id'))
    restaurant_ID = Column(Integer, ForeignKey('restaurant.ID'))
    # customer_ID = Column(Integer, ForeignKey('customer.id'))


engine = create_engine('sqlite:///p.db')
Base.metadata.create_all(engine)
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()


def addToDB():
    complain = Complain(name=1)
    session.add(complain)
    session.commit()

