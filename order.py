import os
import sys
from datetime import datetime
from sqlalchemy import Column, ForeignKey, Integer, String, DateTime, Float , Boolean , FLOAT
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy.orm import relationship
from sqlalchemy import Table, create_engine

Base = declarative_base()

engine = create_engine('sqlite:///Phonograph.db')
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()


class order(Base):
    __tablename__ = 'order'
    # Here we define columns for the table order
    # Notice that each column is also a normal Python instance attribute.
    ID = Column(Integer, primary_key=True, autoincrement=True)
    content = Column(String(250), nullable=False)
    customer_id = Column(Integer)  #, ForeignKey('user.ID'))
    restraunt_id = Column(Integer)#  , ForeignKey('restraunt.ID'))
    price = Column(FLOAT)
    callTime = Column(String(50))
    delievery = Column(Boolean)
    branchID = Column(Integer)#, ForeignKey('branch.ID'))
    delevieryTime = Column(String(50))
    done=Column(Boolean)
    def __init__(self,conten="",cust_id=0,rest_id=0,Price=0,Calltime="",Delievery=False,branch_id=0,delivery_time="",Done=False):
        self.content=conten
        self.customer_id=cust_id
        self.restraunt_id=rest_id
        self.callTime=Calltime
        self.price=Price
        self.delievery=Delievery
        self.branchID=branch_id
        self.delevieryTime=delivery_time
        self.done=Done
    def addToTheDB(self):
        session.add(self)
        session.commit()
    def deleteFromDB(self):
        session.delete(self)
        session.commit()
    def selectFromDB(self,cust_id):
        self = session.query(order).filter(order.customer_id == cust_id).first()

class item(Base):
    __tablename__ = 'item'
    # Here we define columns for the table item
    # Notice that each column is also a normal Python instance attribute.
    ID = Column(Integer, primary_key=True)
    name = Column(String(250), nullable=False)
    price = Column(FLOAT)
    def __init__(self,name="",Price=0):
        self.name=name
        self.price=Price
    def addToTheDB(self):
        session.add(self)
        session.commit()
    def deleteFromDB(self):
        session.delete(self)
        session.commit()
    def selectFromDB(self,name):
        self = session.query(item).filter(item.name == name).first()



Base.metadata.create_all(engine)
o=order()
#o.addToTheDB()
o.selectFromDB(1)
