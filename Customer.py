from sqlalchemy.orm import sessionmaker
from sqlalchemy import Column, ForeignKey, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Table, create_engine


Base = declarative_base()


class Customer(Base):
    __tablename__ = 'customer'
    ID = Column(Integer, primary_key=True)
    name = Column(String)
    Phone = Column(Integer)
    #CallsLog = Column()
    # Orders = Column()


engine = create_engine('sqlite:///Phonograph.db')
Base.metadata.create_all(engine)
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()


def addToDB(name):
    customer = Customer(name=name)
    session.add(customer)
    session.commit()


#def editInDB():

addToDB("mariam")

