import os
import sys
from datetime import datetime
from sqlalchemy import Column, ForeignKey, Integer, String, DateTime, Float
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from sqlalchemy import Table, create_engine

Base = declarative_base()


class Song(Base):
    __tablename__ = 'complain'
    # Here we define columns for the table Song
    # Notice that each column is also a normal Python instance attribute.
    ID = Column(Integer, primary_key=True)
    branch_ID = Column(Integer, ForeignKey('customer.id'))
    restaurant_ID = Column(Integer, ForeignKey('customer.id'))
    customer_ID = Column(Integer, ForeignKey('customer.id'))
    # relations


class Restaurant(Base):
    __tablename__ = 'restaurant'
    ID = Column(Integer, primary_key=True)
    name = Column(String)
    branchs = Column()

