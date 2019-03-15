from datetime import datetime
from sqlalchemy import Column, ForeignKey, Integer, String, DateTime, Float, Boolean
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from sqlalchemy import Table, create_engine

Base = declarative_base()


class reservation(Base):
    __tablename__ = 'reservation'
    ID = Column(Integer, primary_key=True)
    customerID = Column(Integer, nullable=False, ForeignKey('customer.ID'))
    restaurantID = Column(Integer, nullable=False, ForeignKey('restaurant.ID'))
    branchID = Column(Integer, nullable=False, ForeignKey('branch.ID'))
    numOfPeople = Column(Integer)
    timeReserved = Column(DateTime)
    tables = Column(list<table>)

    # relations
    customer = relationship("customer", back_populates="reservations")
    branch = relationship("branch", back_populates="reservations")
    restaurant = relationship("restaurant", back_populates="reservations")

