import pandas as pd
import itertools as iter

class SimpleLinearRegression:

    def __init__(self, path, feature, outcome):
        self.path = path
        self.feature = feature
        self.outcome = outcome
        self.featureData = None
        self.outcomeData = None
        self.readCSV()
        self.buildModel()

    def readCSV(self):
        source = pd.read_csv(self.path)
        self.featureData = source[self.feature]
        self.outcomeData = source[self.outcome]

    def buildModel(self):
        featureMean = self.featureData.mean()
        outcomeMean = self.outcomeData.mean()
        num = den = 0

        for f,o in iter.izip(self.featureData, self.outcomeData):
            num += (f - featureMean) * (o - outcomeMean)
            den += (f - featureMean) * (f - featureMean)

        self.slope = num / den
        self.intercept = outcomeMean - self.slope * featureMean
        print 'Model built successfully'

    def predict(self, x):
        return self.intercept + self.slope * x

    def getSlope(self):
        return self.slope

    def getIntercept(self):
        return self.intercept

    def getTrainRMSE(self):
        return self.getRMSE(self.featureData, self.outcomeData)

    def getRMSE(self, fdata, odata):
        rss = []
        for f,o in iter.izip(fdata, odata):
            rss.append((o - self.predict(f)) ** 2)

        return sum(rss)/len(rss);

    def getTestRMSE(self, path):
        source = pd.read_csv(path)
        fdata = source[self.feature]
        odata = source[self.outcome]
        return self.getRMSE(fdata, odata)
