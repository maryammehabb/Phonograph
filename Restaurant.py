from sqlalchemy import Column, ForeignKey, Integer, String, create_engine, Table
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, relationship

Base = declarative_base()


restaurant_branches = Table('restaurant_branches', Base.metadata,
                       Column('branch_id', Integer, ForeignKey('branch.id')),
                       Column('restaurant_id', Integer,
                              ForeignKey('restaurant.id'))
                       )

class Restaurant(Base):
    __tablename__ = 'restaurant'
    ID = Column(Integer, primary_key=True)
    name = Column(String(50))
'''
    branchs = Column([])
    locations = Column()
    openTime = Column()
    closeTime = Column()'''
    # relations
    branchs = relationship(Branch, secondary=restaurant_branches,
                         back_populates='restaurantt', lazy='dynamic')


engine = create_engine('sqlite:///Phonograph.db')
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
        for r in restaurants:
            print(r.name)

addToDB("mariam")