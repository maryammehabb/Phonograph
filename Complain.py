from sqlalchemy.orm import sessionmaker
from sqlalchemy import Column, ForeignKey, Integer
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Table, create_engine
import Restaurant

Base = declarative_base()
r= Restaurant()


class Complain(Base):
    __tablename__ = 'complain'
    ID = Column(Integer, primary_key=True)
    name = Column(Integer)
    # branch_ID = Column(Integer, ForeignKey('branch.id'))
    restaurant_ID = Column(Integer, ForeignKey('r.ID'))
    # customer_ID = Column(Integer, ForeignKey('customer.id'))
    #content = Column()


engine = create_engine('sqlite:///p.db')
Base.metadata.create_all(engine)
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()


def addToDB(name):
    complain = Complain(name=name)
    session.add(complain)
    session.commit()


def DeleteFromDB(ID):
    for c in session.query(Complain).filter(Complain.ID == ID):
        session.delete(c)
        session.commit()


def RetriveFromDB():
    complains = session.query(Complain).all()
    if (complains != None):
        print()
        for c in complains:
            print(Complain.name)

addToDB("mariam")

