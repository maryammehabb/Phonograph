import os
import sys
from datetime import datetime
from sqlalchemy import Column, ForeignKey, Integer, String, DateTime, Float, TIME, Boolean, TEXT
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from sqlalchemy import Table, create_engine
from sqlalchemy.orm import sessionmaker

Base = declarative_base()


class Restaurant(Base):
    __tablename__ = 'restaurant'
    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String(50))
    # To Be changed later to the proper column type for the next 3 columns
    timeStart = Column(String())
    timeEnd = Column(String())
    kidsMenue= Column(Boolean(250), nullable=False)
    # relations
    branches = relationship('Branch', back_populates="restaurant")
    items = relationship('Item', back_populates="restaurant")
    complaints = relationship('Complaint', back_populates="restaurant")


class Branch(Base):
    __tablename__ = 'branch'
    id = Column(Integer, primary_key=True, autoincrement=True)
    # To Be changed later to the proper column type
    address = Column(String(250), nullable=False)
    numOfTables = Column(Integer)
    delivery = Column(Boolean(250), nullable=False)
    resID = Column(Integer, ForeignKey('restaurant.id'))
    kidsArea= Column(Boolean(250), nullable=False)
    smokingArea= Column(Boolean(250), nullable=False)

    # relations
    restaurant = relationship('Restaurant', back_populates='branches')
    tables = relationship('Table', back_populates='branch')


class Table(Base):
    __tablename__ = 'Table'
    id =Column(Integer, primary_key=True, autoincrement=True)
    branchID = Column(Integer, ForeignKey('branch.id'))
    numOfSeats = Column(Integer)
    reserved = Column(Boolean)
    # relations
    branch = relationship('Branch', back_populates='tables')


class Complaint(Base):
    __tablename__ = 'complaint'
    id = Column(Integer, primary_key=True, autoincrement=True)
    branchID = Column(Integer, ForeignKey('branch.id'))
    resID = Column(Integer, ForeignKey('restaurant.id'))
    file = Column(TEXT)
    user_ID = Column(Integer, ForeignKey('User.id'))
    # relations
    restaurant = relationship('Restaurant', back_populates="complaints")


class Item(Base):
    __tablename__ = 'Item'
    id =Column(Integer, primary_key=True, autoincrement=True)
    resID = Column(Integer, ForeignKey('restaurant.id'))
    name = Column(String)
    price = Column(Float)
    orderID = Column(Integer, ForeignKey('Order.id'), nullable=True)
    kids= Column(Boolean(250), nullable=False)
    # relations
    restaurant = relationship('Restaurant', back_populates="items")
    order = relationship('Order', back_populates="content")



class User(Base):
    __tablename__ = 'User'
    id = Column(Integer, primary_key=True, autoincrement=True)
    mail = Column(String(250), nullable=False)
    password = Column(String(250), nullable=False)
    name = Column(String)
    phone = Column(TEXT)
    address = Column(TEXT)


class Admin(Base):
    __tablename__ = 'Admin'
    id = Column(Integer, primary_key=True, autoincrement=True)
    mail = Column(String(250), nullable=False)
    password = Column(String(250), nullable=False)
    resID = Column(Integer, ForeignKey('restaurant.id'))


class Reservation(Base):
    __tablename__ = 'Reservation'
    id =Column(Integer, primary_key=True, autoincrement=True)
    userID = Column(Integer, ForeignKey('User.id'))
    numOfPeople = Column(Integer)
    branchID = Column(Integer, ForeignKey('branch.id'))
    tableID = Column(Integer, ForeignKey('Table.id'))
    resID = Column(Integer, ForeignKey('restaurant.id'))
    # To Be changed later to the proper column type
    timeReserved = Column(TEXT)
    timeMade = Column(TEXT)


class Order(Base):
    __tablename__ = 'Order'
    id =Column(Integer, primary_key=True, autoincrement=True)
    numOfOrders= Column (Integer)
    items= Column (TEXT)
    time = Column(TEXT)
    delivery = Column(Boolean)
    price = Column(Float)
    resID = Column(Integer, ForeignKey('restaurant.id'))
    timeDelivered = Column(TEXT)
    done = Column(Boolean)
    userID = Column(Integer, ForeignKey('User.id'))
    # relations

    content = relationship('Item', back_populates="order")


engine = create_engine('sqlite:///PH.db')
Base.metadata.create_all(engine)
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()
# r=Reservation(ID=0, CusID=1, numOfPeople=8, BranchID=0, tableID=0,resID=0, timeReserved="6", timeMade="9")
#album = Restaurant(id=0, name="nour",timeStart="9",timeEnd="12",locations="hdhhgygdhdgfuyehduhfsufhdjsfhuhjdhf" )
# br= Branch(ID=0,Address="dhsjhsjfsf",numOfTables=5, delivery=True,resID=0)
# t=table(ID=0, branchID=0, numOfSeats=4,reserved=False)
# g=Item(ID=0,resID=0, name="fdjkdfhujhdfurhfudf", price=89.3)
#u= User(id=0,password="0983",mail="fdfsds",name="dsde",address="rwdsfsf",phone="dhsjds")
#session.add(u)
#session.commit()

s = session.query(User).filter(User.id == 1).first()
s.name = "edited"
session.commit()

