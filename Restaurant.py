from sqlalchemy import Column, ForeignKey, Integer, String, create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

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



engine = create_engine('sqlite:///p.db')
Base.metadata.create_all(engine)
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()

def addToDB(name):
    restaurant = Restaurant(name=name)
    session.add(restaurant)
    session.commit()


def DeleteFromDB(name):
    for c in session.query(Restaurant).filter(Restaurant.name == name):
        session.delete(c)
        session.commit()


def RetriveFromDB():
    restaurants = session.query(Restaurant).all()
    if (restaurants != None):
        print()
        for c in restaurants:
            print(Restaurant.name)

addToDB("mariam")