from datetime import datetime
from sqlalchemy import Column, ForeignKey, Integer, String, DateTime, Float, Boolean
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from sqlalchemy import Table, create_engine

Base = declarative_base()

class table(Base):
    __tablename__ = 'table'
    ID = Column(Integer, primary_key=True)
    branchID = Column(Integer, nullable=False, ForeignKey('branch.ID'))
    numOfSeats = Column(Integer)
    reserved = Column(Boolean)

    # relations
    branch = relationship("customer", back_populates="tables")
