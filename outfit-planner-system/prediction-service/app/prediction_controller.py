from bson.json_util import dumps
from flask import jsonify, request, Blueprint
from werkzeug.wrappers import Request
from app.prediction import predict
import sys

prediction_controller = Blueprint('prediction', __name__)

@prediction_controller.route("/predict", methods=["post"])
def predict_outfits():
    req = request.get_json()
    predictions = predict(req['outfits'], req['weatherConditions'])

    return toRest({"predictions": predictions})

def toRest(model):
    return jsonify(dumps(model))
