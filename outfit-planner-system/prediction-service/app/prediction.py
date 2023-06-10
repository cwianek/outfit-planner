import pymongo
from pymongo import ReturnDocument
import pandas as pd
from sklearn import preprocessing
from sklearn.neighbors import KNeighborsClassifier
import numpy as np
from bson.json_util import dumps
from matplotlib import pyplot as plt
import random
import os, sys, io
import base64
import scipy.sparse as sp

weatherCodeCategories = [
 "0",
 "1", "2", "3",
 "45", "48",
 "51", "53", "55",
 "56", "57",
 "61", "63", "65",
 "66", "67",
 "71", "73", "75",
 "77",
 "80", "81", "82",
 "85", "86",
 "95",
 "96", "99"
]

dataMapper = lambda outfit, addCustomFeatures: {
    "temp": outfit["weatherConditions"]["temperature"],
    "wind_speed": outfit["weatherConditions"]["windSpeed"],
    "weather_code": outfit["weatherConditions"]["weatherCode"]
}

def transform_features(X, min_max_scaler, categorical_scaler, fit = False):
    numeric_X = X.loc[:, X.columns != 'weather_code']
    categorical_X = X.loc[:, X.columns == 'weather_code']

    if fit == True:
        min_max_scaler.fit(numeric_X)
        categorical_scaler.fit(categorical_X)

    numeric_X_scaled = min_max_scaler.transform(numeric_X)
    categorical_X_scaled = categorical_scaler.transform(categorical_X)

    X_scaled = np.hstack((numeric_X_scaled, categorical_X_scaled.toarray()))

    return X_scaled

def transform_data(X):
    min_max_scaler = preprocessing.MinMaxScaler()
    categorical_scaler = preprocessing.OneHotEncoder(categories=[weatherCodeCategories])
    X_scaled = transform_features(X, min_max_scaler, categorical_scaler, True)

    return X_scaled, min_max_scaler, categorical_scaler

def get_XY(outfits, addCustomFeatures):
    data = [dataMapper(outfit, addCustomFeatures) for outfit in outfits ]
    y_data = pd.DataFrame(outfits)
    Y = y_data.loc[:, y_data.columns == "outfitId"]

    df = pd.DataFrame(data)
    X = df.loc[:, df.columns != 'outfitId']

    return X, Y

def predict_new_case(neigh, min_max_scaler, categorical_scaler, weather, addCustomFeatures):
    newOutfit = dict({
        "weatherConditions": weather
    })
    newOutfitWeighted = dataMapper(newOutfit, addCustomFeatures)
    newCase = [newOutfitWeighted]
    newCaseDf = pd.DataFrame(newCase)
    scaled = transform_features(newCaseDf[0:1], min_max_scaler, categorical_scaler)
    pred_proba = neigh.predict_proba(scaled)

    return pred_proba[0]

def predict_probabilities(weather, X, Y, addCustomFeatures):
    X_scaled, min_max_scaler, categorical_scaler = transform_data(X)

    n_neighbors = max(1, min(len(X_scaled) - 1, 3))
    neigh = KNeighborsClassifier(n_neighbors=n_neighbors)
    neigh.fit(X_scaled, Y.values.ravel())

    pred_proba = predict_new_case(neigh, min_max_scaler, categorical_scaler, weather, addCustomFeatures)
    zipped = zip(neigh.classes_, pred_proba)
    sortedOutfits = sorted(zipped, key = lambda t: t[1])

    return sortedOutfits

def predict(outfits, weather):
    X, Y = get_XY(outfits, True)
    outfit_id_probability = predict_probabilities(weather, X, Y, True)

    return dict(outfit_id_probability)

def plot(email, weather):
    X, Y = get_XY(email, False)
    sortedOutfits = predict_probabilities(weather, X, Y, False)
    wornIds = [int(x[0]) for x in sortedOutfits]
    outfitId = wornIds[-1]

    colors = [np.where(wornIds == y['outfitId'])[0] for i, y in Y.iterrows()]

    X = X.append(weather, ignore_index=True)
    Y = Y.append({'outfitId':'new'}, ignore_index=True)
    colors.append(len(wornIds)-1)

    colors = np.array(colors) / 100

    fig = plt.figure(figsize=(6,6))
    ax = fig.add_subplot(111, projection='3d')

    ax.scatter(X["temp"], X["weather_code"], X["wind_speed"], c=colors)
    for i, row in X.iterrows():
        y = Y.iloc[i]['outfitId']
        x_offset = 0
        y_offset = 0
        z_offset = 0
        ax.text(row["temp"] + x_offset, row["weather_code"] + y_offset, row["wind_speed"] + z_offset, '%s' % (y), size=10, zorder=1, color='k')

    ax.set_xlabel('Temperature [°C]')
    ax.set_ylabel('Weather code')
    ax.set_zlabel('Wind speed [m/s]')

    plt.savefig("visualization.png")

    buf = io.BytesIO()
    plt.savefig(buf, format='png')
    buf.seek(0)

    base64_data = base64.b64encode(buf.read())

    return outfitId, base64_data.decode('ascii')